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

(defn add-user-record [user]
  (sql/with-connection db
                       (sql/insert-record :users user)))

(defn get-user [id]
  (sql/with-connection db
                       (sql/with-query-results
                         res ["select * from users where id = ?" id] (first res))))