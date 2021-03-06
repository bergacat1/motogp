(ns motogp.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [motogp.routes.home :refer [home-routes]]
            [motogp.routes.auth :refer [auth-routes]]
            [motogp.routes.pilots :refer [pilot-routes]]
            [motogp.routes.categories :refer [category-routes]]
            [noir.session :as session]
            [ring.middleware.session.memory
             :refer [memory-store]]
            [noir.validation
             :refer [wrap-noir-validation]]))

(defn init []
  (println "motogp is starting"))

(defn destroy []
  (println "motogp is shutting down"))

(defroutes app-routes
           (route/resources "/")
           (route/not-found "Not Found"))

(def app
  (->
    (handler/site
      (routes category-routes
              pilot-routes
              auth-routes
              home-routes
              app-routes))
    (wrap-base-url)
    (session/wrap-noir-session
      {:store (memory-store)})
    (wrap-noir-validation)))
