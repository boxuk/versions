
(ns boxuk.versions
    (:require [clojure.string :as string]))

(defn- version-parts
    "Break a version string a list of its integer parts"
    [version-string]
    (map #(Integer/parseInt %) 
          (string/split version-string #"\.")))

(defn- zero-pad
    "Zero pad lst to have the same length as the longest of both lists"
    [lst rst]
    (let [lst-length (count lst)
          req-length (max lst-length (count rst))]
        (concat lst (repeat (- req-length lst-length) 0))))

(defn- version-pairs
    "Return pairs of interleaved version numbers"
    [current previous]
    (let [current-parts (version-parts current)
          previous-parts (version-parts previous)]
        (map #(list %1 %2)
              (zero-pad current-parts previous-parts)
              (zero-pad previous-parts current-parts))))

;; Public

(defn later-version?
    "Indicates if next-version is a greater version than current-version"
    [current-version next-version]
    (let [pairs (version-pairs current-version next-version)]
        (loop [[x y] (first pairs) more (rest pairs)]
            (cond
                (or (nil? x) (nil? y)) false
                (> y x) true
                (< y x) false
                :default (recur (first more) (rest more))))))

(defn earlier-version?
    "Indicates if prev-version is less then the current-version"
    [current-version prev-version]
    (not (later-version? current-version
                         prev-version)))

