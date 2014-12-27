(ns motogp.routes.categories
  (:require [compojure.core :refer [defroutes GET POST]]
            [motogp.views.layout :as layout]
            [motogp.models.db :as db]
            [noir.session :as session]
            [noir.response :refer [redirect]]
            [hiccup.form :refer :all]
            [hiccup.element :refer :all]))

(defn show-categories []
  (for [{:keys [name]} (db/read-categories)]
    [:div
     [:h2 name]
     [:p "The following pilots races at this category:"]
     [:ul
      (for [{:keys [name]} (db/read-pilots)]
        [:li name])]]))


(defn categories [& [error] ]
  (if (session/get :user)
    (layout/common
      [:h1 "Categories"]
      [:p "Welcome to the categories page"]
      [:p error]
      (show-categories)
      [:hr]
      (form-to [:post "/categories"]
               [:p "Name:"]
               (text-field "name")
               [:br]
               (submit-button "Save"))
      (link-to "/" "Torna a l'inici"))
    (redirect "/login")))


(defn save-category [name]
  (cond
    (empty? name)
    (categories "A category must have a name")
    :else
    (do
      (db/add-category-record {:name name })
      (categories))))

(defroutes category-routes
           (GET "/categories" [] (categories))
           (POST "/categories" [name ] (save-category name)))