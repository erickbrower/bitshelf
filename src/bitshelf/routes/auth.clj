(ns bitshelf.routes.auth
  (:use compojure.core)
  (:require [bitshelf.lib.auth :as handlers]))

(defroutes auth-routes
  (GET "/register" []
       (handlers/register))

  (POST "/register" 
        {params :params}
        (handlers/handle-registration params))

  (GET "/profile" [] (handlers/profile))
  
  (POST "/update-profile" 
        {params :params} 
        (handlers/handle-profile-update params))
  
  (POST "/login" [id pass]
        (handlers/handle-login id pass))

  (GET "/logout" []
        (handlers/logout)))
