(ns ping-urls.pinger
  (:require [org.httpkit.client :as http]
            [clojure.string :as str]
            [org.httpkit.sni-client :as sni-client])
  (:gen-class))

(alter-var-root #'org.httpkit.client/*default-client*
                (fn [_] sni-client/default-client))

(defn erroneous-status? 
  "Checks if the status received is erroneous"
  [status]
  (or (str/starts-with? (str status) "4")
      (str/starts-with? (str status) "5")))

(defn ping-url [url]
  "Pings the url and returns an error message if the url responses with an error"
  (let [options {:timeout 800}]
    (let [{:keys [status error] :as resp} @(http/get url)]
      (if error
        (str url ": FAIL, exception: " (str error))
        (if (erroneous-status? status)
          (str url ": FAIL, status: " status)
          (str url ": SUCCESS: " status))))))

(defn ping-urls-from-list
  "Pings the URLs and then return a log of the result"
  [urls]
  (loop [index 0 log (transient [])]
    (if (< index (count urls))
      (recur (inc index)
             (let [url (get urls index)]
               (conj! log (ping-url url))))
      (persistent! log))))