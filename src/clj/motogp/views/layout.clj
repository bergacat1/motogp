(ns motogp.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to motogp"]
     (include-css "/css/base.css")]
    [:body body]))
