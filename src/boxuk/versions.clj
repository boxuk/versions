
(ns boxuk.versions
  (:require [clojure.string :as string]))

(def unstable-parts
  {"-alpha" ".1."
   "-beta" ".2."
   "-RC" ".3."
   "-SNAPSHOT" ".4"})

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

(defn go-compare
  [current other success failure]
  (boolean (reduce #(if (nil? %1)
                      (let [[a b] %2]
                        (cond (success b a) true
                              (failure b a) false)) %1)
                   nil (version-pairs current other))))

(defn go-filter
  [comparer versions]
  (reduce #(if (or (nil? %1)
                   (comparer %1 %2)) %2
             %1)
          nil versions))

;; Public
;; ------

(defn later-version?
  [current other]
  (go-compare current other > <))

(def earlier-version?
  (complement later-version?))

(defn same-version?
  "Indicates if two version numbers are the same"
  [current other]
  (every? #(= (first %) (second %))
          (version-pairs current other)))

(defn latest-version
  "Return latest version from seq of versions"
  [versions]
  (go-filter later-version? versions))

(defn earliest-version
  "Return earlier version from seq of versions"
  [versions]
  (go-filter earlier-version? versions))

(defn latest-stable
  "Return latest stable version from seq of versions"
  [versions]
  (->> versions
       (filter stable?)
       (latest-version)))

(defn unstable? [version-string]
  (some
    #(.contains version-string %)
    (keys unstable-parts)))

(def stable?
  (complement unstable?))

