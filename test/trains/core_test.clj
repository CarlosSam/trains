(ns trains.core-test
  (:require [clojure.test :refer :all]
            [trains.core :refer :all]))

(deftest a-test
  (testing "calculate-distance"
    (is (= 2 (calculate-distance [:a :b] {:a {:b 2}})))))
