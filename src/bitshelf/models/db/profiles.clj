(in-ns 'bitshelf.models.db)

(defn create-profile [profile]
  (insert profiles
          (values profile)))

(defn update-profile [& [id first-name last-name birthdate timezone]]
  (update profiles 
    (set-fields {:first_name first-name
                 :last_name last-name
                 :birthdate birthdate
                 :timezone timezone 
                 :updated_at [(= (sqlfn now))]})
    (where {:id id})))

(defn get-profile [id]
  (first (select profiles
                 (where {:id id})
                 (limit 1))))
