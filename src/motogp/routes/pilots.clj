(ns motogp.routes.pilots
  (:require [compojure.core :refer [defroutes GET POST]]
            [motogp.views.layout :as layout]
            [motogp.models.db :as db]
            [motogp.routes.home ]
            [noir.session :as session]
            [noir.response :refer [redirect]]
            [hiccup.form :refer :all]
            [hiccup.element :refer :all]))

(defn show-pilots []
  (for [{:keys [name country category company]} (db/read-pilots)]
    [:div
     [:h2 name]
     [:ul
      [:li (str "The pilor is from " country)]
      [:li (str "He compete in the " category "category and for the company " company)]]]))

(defn pilots [& [error] ]
  (if (session/get :user)
    (layout/common
      [:h1 "Pilots"]
      [:p "Welcome to the pilots page"]
      [:p error]
      (show-pilots)
      [:hr]
      (form-to [:post "/pilots"]
               [:p "Name:"]
               (text-field "name")
               [:p "Country:"]
               (text-field "country")
               [:p "Category:"]
               [:select {:name "category"} (select-options (map (comp reverse vec) (db/read-categories)))]
               [:p "Company:"]
               (text-field "company")
               [:br]
               (submit-button "Save"))
      (link-to "/" "Torna a l'inici"))
    (redirect "/login")))


(defn save-pilot [name country category company]
  (cond
    (empty? name)
    (pilots "A pilot must have a name")
    (empty? country)
    (pilots "Where is the pilot from?")
    (empty? category)
    (pilots "Which is the category where the pilot compete?")
    (empty? company)
    (pilots "Pilot must have a company")
    :else
    (do
      (db/add-pilot-record {:name name :country country :category category :company company})
      (pilots))))

(defroutes pilot-routes
           (GET "/pilots" [] (pilots))
           (POST "/pilots" [name country category company] (save-pilot name country category company)))