(ns motogp.routes.home
  (:require [compojure.core :refer :all]
            [motogp.views.layout :as layout]
            [noir.session :as session]
            [noir.response :refer [redirect]]))

(defn home []
  (if (session/get :user)
                   (layout/common [:h1 {:id "userid"} (str "Hello user: " (session/get :user) ) ])
                   (redirect "/login"))
  )


(defroutes home-routes
  (GET "/" [] (home)))