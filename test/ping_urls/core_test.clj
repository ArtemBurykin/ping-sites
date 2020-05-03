(ns ping-urls.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [ping-urls.core :refer [get-config-filename-from-args
                                    get-show-only-fails-from-args]]))

(deftest get-config-filename-from-args-test
  (testing "core/get-config-filename-from-args"
    (is (= "config.test" (get-config-filename-from-args ["config.test"])))
    (is (thrown-with-msg? Exception #"Cannot find the configuration file"
                          (get-config-filename-from-args [])))
    (is (thrown-with-msg? Exception #"Cannot find the configuration file"
                          (get-config-filename-from-args ["--show-only-fails"])))
    (is (= "config.test" (get-config-filename-from-args ["config.test" "--show-only-fails"])))
    (is (thrown-with-msg? Exception #"Cannot determine a filename for the configuration file"
                          (get-config-filename-from-args ["config.test" "another.test"])))))

(deftest get-show-only-fails-from-args-test
  (testing "core/get-show-only-fails-from-args"
    (is (true? (get-show-only-fails-from-args ["--show-only-fails"])))
    (is (false? (get-show-only-fails-from-args [])))
    (is (true? (get-show-only-fails-from-args ["test.config" "--show-only-fails"])))))