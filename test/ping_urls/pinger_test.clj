(ns ping-urls.pinger-test
  (:require [org.httpkit.fake :refer [with-fake-http]]
            [clojure.test :refer [testing deftest is]]
            [ping-urls.pinger :refer [ping-urls-from-list]]))

(defn in? [coll needle]
  "To check if the value contains in the collection. Test purposes only, as it does not handle nil etc."
  (boolean (some #{needle} coll)))

(deftest ping-urls-from-list-test
  (testing "pinger/ping-urls-from-list")
  (with-fake-http [{:url "http://successful-site.com"} {:status 200 :body "ok"}
                   {:url "http://error-site.com"} {:error "internal error"}
                   {:url "http://not-successful-site"} {:status 404 :body "Not found"}]

                  (let [result-with-successful
                        (ping-urls-from-list
                          ["http://successful-site.com" "http://error-site.com" "http://not-successful-site"]
                          false)]
                    (is (in? result-with-successful "http://successful-site.com: SUCCESS: 200"))
                    (is (in? result-with-successful "http://error-site.com: FAIL, exception: internal error"))
                    (is (in? result-with-successful "http://not-successful-site: FAIL, status: 404")))

                  (let [result-without-successful
                        (ping-urls-from-list
                          ["http://successful-site.com" "http://error-site.com" "http://not-successful-site"]
                          true)]
                    (print result-without-successful)
                    (is (= 2 (count result-without-successful)))
                    (is (in? result-without-successful "http://error-site.com: FAIL, exception: internal error"))
                    (is (in? result-without-successful "http://not-successful-site: FAIL, status: 404")))))

