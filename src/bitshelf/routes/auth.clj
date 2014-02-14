(ns bitshelf.routes.auth
  (:use compojure.core)
  (:require [bitshelf.views.layout :as layout]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.validation :as vali]
            [noir.util.crypt :as crypt]
            [bitshelf.models.db :as db]))

(defn valid? [email username pass pass-confirm]
  (vali/rule (vali/has-value? email)
             [:email "Email Address is required"])
  (vali/rule (vali/has-value? username)
             [:username "Username is required"])
  (vali/rule (vali/min-length? pass 8)
             [:pass "Password must be at least 8 characters"])
  (vali/rule (= pass pass-confirm)
             [:pass-confirm "Entered passwords do not match"])
  (not (vali/errors? :email :username :pass :pass-confirm)))

(defn register [& [email username]]
  (layout/render
    "registration.html"
    {:email-error (vali/on-error :email first)
     :username-error (vali/on-error :username first)
     :pass-error (vali/on-error :pass first)
     :pass-confirm-error (vali/on-error :pass-confirm first)}))

(defn handle-registration [email username pass pass-confirm]
  (if (valid? email username pass pass-confirm)
    (try
      (do
        (let [user (db/create-user {:username username 
                                    :pass (crypt/encrypt pass)})] 
          (db/create-profile {:email email :users_id (user :id)})
          (session/put! :username username)
          (session/put! :user-id (user :id)))
        (resp/redirect "/"))
      (catch Exception ex
        (vali/rule false [:id (.getMessage ex)])
        (register)))
    (register email)))

(defn profile []
  (layout/render
    "profile.html"
    {:user (db/get-user-by-email (session/get :user-email))}))

(defn update-profile [{:keys [first-name last-name email]}]
  (db/update-user (session/get :user-id) first-name last-name email)
  (profile))

(defn handle-login [email pass]
  (let [user (db/get-user-by-email email)]
    (if (and user (crypt/compare pass (:pass user)))
      (session/put! :user-email (user :email))))
    (resp/redirect "/"))

(defn logout []
  (session/clear!)
  (resp/redirect "/"))

(defroutes auth-routes
  (GET "/register" []
       (register))

  (POST "/register" [email username pass pass-confirm]
        (handle-registration email username pass pass-confirm))

  (GET "/profile" [] (profile))
  
  (POST "/update-profile" {params :params} (update-profile params))
  
  (POST "/login" [id pass]
        (handle-login id pass))

  (GET "/logout" []
        (logout)))
