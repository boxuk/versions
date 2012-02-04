
(ns boxuk.versions
    (:require [clojure.string :as string]))

(defn- version-parts
    "Break a version string a list of its integer parts"
    [version-string]
    (map #(Integer/parseInt %) 
          (string/split version-string #"\.")))

(defn- version-pairs
    "Create list of pairs of version numbers"
    [current other]
    (let [other-parts (version-parts other)
          current-parts (version-parts current)]
        (map #(list %2 %1)
              other-parts
              (concat current-parts (repeat (count other-parts) 0)))))

(defmacro defcompare [name group operator]
    "Define a version comparer function"
    `(defn ~name
        [current# other#]
        (boolean (~group #(~operator (second %) (first %))
                          (version-pairs current# other#)))))

;; Public

(defcompare later-version? some >)

(defcompare earlier-version? some <)

(defcompare same-version? every? =)

