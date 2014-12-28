(ns motogp.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))

(def db {:classname   "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname     "db.sq3"})

(defn create-user-table []
  (sql/with-connection
    db
    (sql/create-table
      :users
      [:id "varchar(20) PRIMARY KEY"]
      [:pass "varchar(100)"])))

(defn create-pilot-table []
  (sql/with-connection
    db
    (sql/drop-table :pilots)
    (sql/create-table
      :pilots
      [:name "varchar(50) PRIMARY KEY"]
      [:country "varchar(20)"]
      [:category "varchar(20)"]
      [:company "varchar(20)"])))

(defn create-category-table []
  (sql/with-connection
    db
    (sql/create-table
      :category
      [:name "varchar(50) PRIMARY KEY"])))

(defn add-user-record [user]
  (sql/with-connection db
                       (sql/insert-record :users user)))

(defn add-pilot-record [pilot]
  (sql/with-connection db
                       (sql/insert-record :pilots pilot)))
(defn get-user [id]
  (sql/with-connection db
                       (sql/with-query-results
                         res ["select * from users where id = ?" id] (first res))))

(defn read-pilots []
  (sql/with-connection db
                       (sql/with-query-results
                         res ["select * from pilots" ] res)))

(defn read-categories []
  (sql/with-connection db
                       (sql/with-query-results
                         res ["select * from category" ] (doall res))))

(defn add-category-record [category]
  (sql/with-connection db
                       (sql/insert-record :category category)))