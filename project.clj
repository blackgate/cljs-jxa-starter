(defproject cljs-jxa-starter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.48"]]

  :plugins [[lein-cljsbuild "1.0.6"]]
  :clean-targets ^{:protect false} ["resources/compiled" "target"]

  :cljsbuild {
    :builds [{:id "core"
              :source-paths ["src/cljs_jxa_starter"]
              :compiler {:output-to "resources/compiled/core.js"
                         :main cljs-jxa-starter.core
                         :optimizations :whitespace
                         :pretty-print true}}]})
