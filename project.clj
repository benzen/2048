(defproject deux-mile-quarante-huit "0.1.0-SNAPSHOT"
  :description "a 2048 clone written with clojurescript and reagent"
  :url "https://github.com/benzen/2048"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2816"]
                 [reagent "0.5.0" ]]

  :node-dependencies [[source-map-support "0.2.8"] 
                      [phantomjs "1.9.16"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]]

  :source-paths ["src" "test" "target/classes"]
  :clean-targets ["out" "out-adv"]

  :cljsbuild {
    :builds [
             {:id "dev"
              :source-paths ["src"]
              :compiler {
                :main deux-mile-quarante-huit.core
                :output-to "out/deux_mile_quarante_huit.js"
                :output-dir "out"
                :optimizations :none
                :cache-analysis true
                :source-map true}}
             {:id "test"
              :source-paths ["src" "test"]
              :notify-command ["phantomjs" "phantom/unit-test.js" "phantom/unit-test.html"]
              :compiler {
                :main deux-mile-quarante-huit.core
                :output-to "out-test/deux_mile_quarante_huit-test.js"
                :output-dir "out-test"
                :cache-analysis true
                }}

             {:id "release"
              :source-paths ["src"]
              :compiler {
                :main deux-mile-quarante-huit.core
                :output-to "out-adv/deux_mile_quarante_huit.min.js"
                :output-dir "out-adv"
                :optimizations :advanced
                :pretty-print false}}],
      :test-commands {"test" ["phantomjs" "phantom/unit-test.js" "phantom/unit-test.html"]}})
