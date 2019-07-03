(ns ping-urls.slack
  (:require [clojure.data.json :as json]
            [clojure.string :as str]
            [org.httpkit.client :as http])
  (:gen-class))

(defn send-to-slack 
  "Sends the log of pings to the url of slack webhook, returns a status of the request"
  [url log]
  (let [text (str/join "\n" log)]
    (let [{:keys [status headers body error] :as resp}
          @(http/post url {:body (json/write-str {:text text})})]
      (str body " " status))))

