(in-ns 'bitshelf.models.db)

(defn create-user [user]
  (insert users 
          (values user)))

(defn update-user [id username]
  (update users 
    (set-fields {:email username
                 :updated_at [(= (sqlfn now))]})
    (where {:id id})))

(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

(defn get-user-by-email [email]
  (first (select users
                 (where {:email email})
                 (limit 1))))

