(ns ping-urls.pinger-test
  (:use org.httpkit.fake)
  (:require [clojure.test :refer :all]
            [org.httpkit.client :as http]
            [ping-urls.pinger :refer :all]))

(deftest erroneous-status-test
  (testing "pinger/erroneous-status?")
  (is (erroneous-status? 404))
  (is (erroneous-status? 500))
  (is (false? (erroneous-status? 201)))
  (is (false? (erroneous-status? 102)))
  (is (false? (erroneous-status? 302))))

(deftest ping-url-test 
  (testing "pinger/ping-url")
  (with-fake-http [{:url "http://not-successful-site.com"} {:status 404 :body "Not found"}
                   {:url "http://successful-site.com"} {:status 200 :body "ok"}
                   {:url "http://error-site.com"} {:error "internal error"}]
    
    (is (= "http://not-successful-site.com: FAIL, status: 404" 
           (ping-url "http://not-successful-site.com")))
    (is (= "http://error-site.com: FAIL, exception: internal error" 
           (ping-url "http://error-site.com")))
    (is (= "http://successful-site.com: SUCCESS: 200"
           (ping-url "http://successful-site.com")))))

(deftest ping-urls-from-list-test 
  (testing "pinger/ping-urls-from-list")
  (with-fake-http [{:url "http://successful-site.com"} {:status 200 :body "ok"}
                   {:url "http://error-site.com"} {:error "internal error"}]
    
    (is (= ["http://successful-site.com: SUCCESS: 200" "http://error-site.com: FAIL, exception: internal error"]
           (ping-urls-from-list ["http://successful-site.com" "http://error-site.com"])))))

