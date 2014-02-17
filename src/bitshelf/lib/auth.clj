(ns bitshelf.lib.auth
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

(defn handle-registration [{:keys [email username pass pass-confirm]}]
  (if (valid? email username pass pass-confirm)
    (try
      (do
        (let [user (db/create-user-from-registration email 
                                                     username 
                                                     (crypt/encrypt pass))] 
        (session/put! :user-id (user :id)))
        (session/put! :username username)
        (resp/redirect "/"))
      (catch Exception ex
        (vali/rule false [:email (.getMessage ex)])
        (vali/rule false [:username (.getMessage ex)])
        (register)))
    (register email username)))

(defn profile []
  (layout/render
    "profile.html"
    {:user (db/get-user (session/get :user-id))}))

(defn handle-profile-update [{:keys [first-name last-name email]}]
  (db/update-user (session/get :user-id) first-name last-name email)
  (profile))

(defn handle-login [login pass]
  (let [user (db/get-user-by-login login)]
    (if (and user (crypt/compare pass (:pass user)))
      (session/put! :user-email (user :email))))
    (resp/redirect "/"))

(defn logout []
  (session/clear!)
  (resp/redirect "/"))
