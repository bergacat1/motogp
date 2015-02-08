(ns motogp.core
  (:require [org.httpkit.client :as http]))

(def options {:timeout 200             ; ms
              :basic-auth ["user" "pass"]
              :query-params {:param "value" :param2 ["value1" "value2"]}
              :user-agent "User-Agent-string"
              :headers {"X-Header" "Value"}})

(http/get "http://localhost:8080" options
     (fn [{:keys [status headers body error]}] ;; asynchronous response handling
       (if error
         (println "Failed, exception is " error)
         (println "Async HTTP GET: " status))))