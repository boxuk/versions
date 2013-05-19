
(ns boxuk.versions
  (:require [clojure.string :as string]))

(def unstable-parts
  {"-alpha" ".1."
   "-beta" ".2."
   "-RC" ".3."
   "-SNAPSHOT" ".4."})

(defn- map-replace [m text]
  (reduce
    (fn [acc [k v]]
      (string/replace acc (str k) (str v)))
    text m))

(defn- replace-unstable-parts [version-string]
  (map-replace
    unstable-parts
    version-string))

(defn- version-parts
  "Break a version string a list of its integer parts"
  [version-string]
  (map #(Integer/parseInt %)
       (string/split
         (replace-unstable-parts version-string)
         #"\.")))

(defn- version-pairs
  "Create list of pairs of version numbers"
  [current other]
  (let [other-parts (version-parts other)
        current-parts (version-parts current)]
    (map #(list %2 %1)
         other-parts
         (concat current-parts (repeat (count other-parts) 0)))))

(defmacro defcomparer [name success failure]
  "Define a comparer with success and fail comparisons"
  `(defn ~name
     [current# other#]
     (boolean (reduce #(if (nil? %1)
                         (let [[a# b#] %2]
                           (cond (~success b# a#) true
                                 (~failure b# a#) false)) %1)
                      nil (version-pairs current# other#)))))

(defmacro deffilter [name filterer]
  "Define a filterer for a version number"
  `(defn ~name
     [versions#]
     (reduce #(if (or (nil? %1)
                      (~filterer %1 %2)) %2
                %1)
             nil versions#)))

;; Public

(defcomparer later-version? > <)

(defcomparer earlier-version? < >)

(defn same-version?
  "Indicates if two version numbers are the same"
  [current other]
  (every? #(= (first %) (second %))
          (version-pairs current other)))

(deffilter latest-version later-version?)

(deffilter earliest-version earlier-version?)

(defn unstable? [version-string]
  (some
    #(.contains version-string %)
    (keys unstable-parts)))

(def stable?
  (complement stable?))

