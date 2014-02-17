(in-ns 'bitshelf.models.db)

(defn create-user [user]
  (insert users 
          (values user)))

(defn update-user [id username]
  (update users 
    (set-fields {:username username
                 :updated_at [(= (sqlfn now))]})
    (where {:id id})))

(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

(defn get-user-by-login [login]
  (first 
    (select users 
      (join profiles (= :profiles.users_id :id))
      (where (or (= :profiles.email login)
                 (= :username login))))))


