
(ns boxuk.test.versions
  (:use [boxuk.versions])
  (:use [clojure.test]))

(deftest test-later-version?
    (is (later-version? "1.0.0" "1.0.1"))
    (is (later-version? "1.0.0" "2.0.1"))
    (is (later-version? "1.1.0" "1.2.1"))
    (is (not (later-version? "1.0.1" "1.0.0")))
    (is (not (later-version? "2.0.1" "2.0.0")))
    (is (not (later-version? "1.2.1" "1.1.0"))))

(deftest test-earlier-version?
    (is (earlier-version? "1.0.0" "0.0.9"))
    (is (not (earlier-version? "1.0.0" "1.2.0"))))

