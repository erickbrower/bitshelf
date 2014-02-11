(ns bitshelf.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [bitshelf.models.schema :as schema]))

(defdb db schema/db-spec)

(declare users profiles products product-types)

(defentity users
  (entity-fields :id 
                 :email 
                 :password_digest 
                 :created_at 
                 :updated_at)
  (has-one profiles))

(defentity profiles 
  (entity-fields :id 
                 :username 
                 :users_id 
                 :created_at 
                 :updated_at))

;; user data functions
(defn create-user [user]
  (insert users 
          (values user)))

(defn update-user [id username email]
  (update users 
    (set-fields {:username username
                 :email email
                 :updated_at [(= (sqlfn now))]})
    (where {:id id})))

(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

;; profile data functions
(defn create-profile [profile]
  (insert profiles
          (values profile)))

(defn update-profile [id username email]
  (update profiles 
    (set-fields {:username username
                 :email email
                 :updated_at [(= (sqlfn now))]})
    (where {:id id})))

(defn get-profile [id]
  (first (select profiles
                 (where {:id id})
                 (limit 1))))

