(ns ping-urls.pinger
  (:require [org.httpkit.client :as http]
            [clojure.string :as str]
            [org.httpkit.sni-client :as sni-client])
  (:gen-class))

(alter-var-root #'org.httpkit.client/*default-client*
                (fn [_] sni-client/default-client))

(defn- erroneous-status?
  "Checks if the status received is erroneous"
  [status]
  (or (str/starts-with? (str status) "4")
      (str/starts-with? (str status) "5")))

(defn- get-status-string-from-response
  "Gets a status string from the request"
  [{:keys [status error opts]} show-only-fails]
  (let [url (:url opts)]
    (if error
      (str url ": FAIL, exception: " (str error))
      (if (erroneous-status? status)
        (str url ": FAIL, status: " status)
        (when-not show-only-fails (str url ": SUCCESS: " status))))))

(defn ping-urls-from-list
  "Pings the URLs and then returns a log of the result"
  [urls show-only-fails]
  (let [res-futures (doall (map http/get urls))
        get-status (fn [response] (get-status-string-from-response @response show-only-fails))]
    (filter (complement nil?) (map get-status res-futures))))

