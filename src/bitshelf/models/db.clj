(ns bitshelf.models.db
  (:use korma.core
        [korma.db :only (defdb transaction)])
  (:require [bitshelf.models.schema :as schema]))

(defdb db schema/db-spec)

(declare users 
         profiles 
         feeds 
         feed-urls 
         feed-types 
         subscriptions 
         issues 
         issue-layouts)

(defentity users
  (has-one profiles)
  (entity-fields :id 
                 :email 
                 :password_digest 
                 :created_at 
                 :updated_at))

(defentity profiles 
  (belongs-to users)
  (entity-fields :id 
                 :first_name
                 :last_name
                 :birthdate
                 :timezone
                 :users_id 
                 :created_at 
                 :updated_at))

(defentity subscriptions
  (belongs-to users)
  (belongs-to feeds)
  (entity-fields :id
                 :deliver_at
                 :users_id
                 :feeds_id
                 :created_at
                 :updated_at))

(defentity issues
  (belongs-to subscriptions)
  (belongs-to issue-layouts)
  (entity-fields :id
                 :title
                 :subscriptions_id
                 :issue_layouts_id
                 :created_at
                 :updated_at))

(defentity files
  (belongs-to issues)
  (entity-fields :id
                 :filetype
                 :path
                 :created_at
                 :updated_at))

(defentity feeds
  (has-many feed-urls)
  (entity-fields :id
                 :title
                 :created_at
                 :updated_at))

(defentity feed-urls
  (belongs-to feeds)
  (belongs-to feed-types)
  (entity-fields :id
                 :url
                 :created_at
                 :updated_at))

(defentity feed-types
  (has-many feed-urls)
  (entity-fields :id
                 :name
                 :created_at
                 :updated_at))

(defentity issue-layouts
  (has-many subscriptions)
  (entity-fields :id
                 :title
                 :template
                 :created_at
                 :updated_at))


(load "functions/users")
(load "functions/profiles")
(load "procedures/auth")
(load "procedures/utils")
