(ns bitshelf.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [bitshelf.models.schema :as schema]))

(defdb db schema/db-spec)

(declare users profiles)

(defentity users
  (entity-fields :id 
                 :email 
                 :password_digest 
                 :created_at 
                 :updated_at)
  (has-one profiles))

(defentity profiles 
  (entity-fields :id 
                 :first_name
                 :last_name
                 :birthdate
                 :timezone
                 :users_id 
                 :created_at 
                 :updated_at)
  (belongs-to users))

(load "db/users")
(load "db/profiles")
