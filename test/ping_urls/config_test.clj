(ns ping-urls.config-test
  (:require [clojure.test :refer :all]
            [test-with-files.core :refer [with-files public-dir]]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [ping-urls.config :refer :all]))

(deftest config-parse-config-test
  (testing "config/parse-config"
    (with-files [["/config.json" (json/write-str {:urls ["http://url1" "http://url2"] :slackHook "http://slack"})]]
      (is (= {:urls ["http://url1" "http://url2"] :slackHook "http://slack"}
          (parse-config (io/resource (str public-dir "/config.json"))))))))

(deftest config-get-urls-test
  (testing "config/get-urls"
    (is (= ["http://url1" "http://url2"]
        (get-urls {:urls ["http://url1" "http://url2"] :slackHook "http://slack"})))))

(deftest config-get-slack-hook-test
  (testing "config/get-slack-hook"
    (is (= "http://slack"
        (get-slack-hook {:urls ["http://url1" "http://url2"] :slackHook "http://slack"})))))
