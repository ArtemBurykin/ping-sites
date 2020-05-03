(ns ping-urls.core
  (:require [ping-urls.config :as config]
            [ping-urls.slack :as slack]
            [ping-urls.pinger :as pinger]
            [clojure.string :as str])
  (:gen-class))

(defn get-config-filename-from-args
  "Returns a config filename from the application cli arguments"
  [args]
  (let [filenames (filter #(not (str/starts-with? % "--")) args)]
    (cond
      (= (count filenames) 0) (throw (Exception. "Cannot find the configuration file"))
      (> (count filenames) 1) (throw (Exception. "Cannot determine a filename for the configuration file"))
      :else (first filenames))))

(defn get-show-only-fails-from-args
  "Returns the value of the option which determines if only fails should be shown"
  [args]
  (> (count (filter #(= % "--show-only-fails") args)) 0))

(defn -main
  "Pings the urls from the config file provided."
  [& args]
  (let [filename (get-config-filename-from-args args)
        show-only-fails (get-show-only-fails-from-args args)
        config (config/parse-config filename)
        urls (config/get-urls config)]
    (print
      (slack/send-to-slack (config/get-slack-hook config)
                           (pinger/ping-urls-from-list urls show-only-fails)))))
