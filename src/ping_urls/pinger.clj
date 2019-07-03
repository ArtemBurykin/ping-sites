(ns ping-urls.pinger
  (:require [org.httpkit.client :as http]
            [clojure.string :as str])
  (:gen-class))

(defn erroneous-status? 
  "Checks if the status received is erroneous"
  [status]
  (str/starts-with? (str status) (or "4" "5")))

(defn print-error-if-request-fails [url]
  "Returns an error message if the url responses with an error"
  (let [options {:timeout 800}]
    (let [{:keys [status error] :as resp} @(http/get url)]
      (if error
        (str url ": FAIL, exception: " (ex-message error))
        (if (erroneous-status? status)
          (str url ": FAIL, status: " status)
          (str url ": Success:" status))))))

(defn ping-urls
  "Pings the URLs and then return a log of the result"
  [urls]
  (loop [index 0 log (transient [])]
    (if (< index (count urls))
      (recur (inc index)
             (let [url (get urls index)]
               (conj! log (print-error-if-request-fails url))))
      (persistent! log))))