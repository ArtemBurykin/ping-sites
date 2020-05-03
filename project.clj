(defproject ping-urls "0.2.0"
  :description "The script allows you to ping pages of a website to check their status"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.4.0-alpha5"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot ping-urls.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev     {:dependencies   [[test-with-files "0.1.1"]
                                        [http-kit.fake "0.2.1"]]
                       :resource-paths ["test/resources"]}})
