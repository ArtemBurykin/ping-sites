(ns ping-urls.config
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn parse-config
  "Parses the config file to a list of its values"
  [filename]
  (json/read-str (slurp filename) :key-fn keyword))

(defn get-urls
  "Retrieves URLs to ping from the config"
  [config]
  (:urls config))

(defn get-slack-hook
  "Retrieves a URL of a slack hook to send a message"
  [config]
  (:slackHook config))