(ns ping-urls.core
  (:require [ping-urls.config :as config]
            [ping-urls.slack :as slack]
            [ping-urls.pinger :as pinger])
  (:gen-class))

(defn get-only-arg-or-fail
  "Return the only argument. If there are few or no arguments it fails"
  [args]
  (if (not= 1 (count args)) (throw (Exception. "must be exactly one argument ")) (first args)))

(defn -main
  "Pings the urls from the config file provided. The onlye argument is a config file name"
  [& args]
  (let [filename (get-only-arg-or-fail args)]
    (let [config (config/parse-config filename)]
      (print (slack/send-to-slack
              (config/get-slack-hook config)
              (pinger/ping-urls-from-list (config/get-urls config)))))))
