(in-ns 'bitshelf.models.db)

(defn create-user-from-registration 
  [email username pass-digest]
  (transaction
    (let [user (create-user {:username username
                             :pass pass-digest})]
      (create-profile {:users_id (user :id) 
                       :email email})
      user)))
