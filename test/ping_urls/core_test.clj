(ns ping-urls.core-test
  (:require [clojure.test :refer :all]
            [ping-urls.core :refer :all]))

(deftest core-get-only-arg-or-fail-test
  (testing "core/get-only-arg-or-fail"
    (is (= "test" (get-only-arg-or-fail ["test"])))
    (is (thrown-with-msg? Exception #"must be exactly one argument" (get-only-arg-or-fail [])))
    (is (thrown-with-msg? Exception #"must be exactly one argument" (get-only-arg-or-fail ["test" "test1"])))))
