
(defproject boxuk/versions "0.6.1"
  :description "Tool for comparing version numbers"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :cljsbuild
    {:builds []
     :crossovers [boxuk.versions]
     :crossover-jar true
     :jar true}
  :hooks [leiningen.cljsbuild])

