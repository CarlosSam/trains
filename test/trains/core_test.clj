(ns trains.core-test
  (:require [clojure.test :refer :all]
            [trains.core :refer :all]))

(deftest testing-app
  (testing "calculate-distance"
    (is (= 2 (calculate-distance [:a :b] {:a {:b 2}})))
    (is (= nil (calculate-distance [:a :c] {:a {:b 2}})))
    (is (= 101 (calculate-distance [:a :c :b] {:a {:b 2 :c 1} :b {:a 10 :c 20} :c {:a 200 :b 100}}))))
 (testing "max stops"
    (is (= #{} (find-trips :x :y 1 {:a {:b 1}})))
    (is (= #{} (find-trips :a :y 1 {:a {:b 1}})))
    (is (= #{[:a :b]} (find-trips :a :b 1 {:a {:b 4 :c 7}})))
    (is (= #{[:a :c]} (find-trips :a :c 1 {:a {:b 4 :c 7} :b {:c 8}})))
    (is (= #{[:a :b :d] [:a :b :d :b :d] [:a :b :c :d] [:a :c :a :b :d] [:a :c :d] [:a :c :d :b :d] [:a :c :a :c :d]} (find-trips :a :d 4 {:a {:b 4 :c 7} :b {:c 8 :d 2} :c {:a 9 :d 2} :d {:b 4}})))
    (is (= #{[:a :b :d :b :d][:a :c :a :b :d][:a :c :d :b :d][:a :c :a :c :d]} (find-trips-exactly :a :d 4 {:a {:b 4 :c 7} :b {:c 8 :d 2} :c {:a 9 :d 2} :d {:b 4}}))))
 (testing "shortest-route"
    (is (= nil (shortest-route :x :y {:a {:b 4}})))
    (is (= nil (shortest-route :a :y {:a {:b 4}})))
    (is (= 4 (shortest-route :a :b {:a {:b 4}})))
    (is (= 7 (shortest-route :a :d {:a {:b 4 :c 10} :b {:c 1 :d 14} :c {:a 2 :d 2} :d {:a 3}}))))
 (testing "find routes"
    (is (= #{} (find-routes :x :y 10 {:a {:b 4}})))
    (is (= #{} (find-routes :a :y 10 {:a {:b 4}})))
    (is (= #{[:a :b]} (find-routes :a :b 10 {:a {:b 4}})))
    (is (= #{[:a :c][:a :b :c][:a :c :d :a :c][:a :c :d :a :b :c][:a :b :c :d :a :c]} (find-routes :a :c 24 {:a {:b 4 :c 3} :b {:c 8} :c {:d 6} :d {:a 2 :b 8}})))))

(deftest expected-test
  (let [distances {:a {:b 5, :d 5, :e 7}, :b {:c 4}, :c {:d 8, :e 2}, :d {:c 8, :e 6}, :e {:b 3}}]
    (is (= 9 (calculate-distance [:a :b :c] distances)))
    (is (= 5 (calculate-distance [:a :d] distances)))
    (is (= 13 (calculate-distance [:a :d :c] distances)))
    (is (= 22 (calculate-distance [:a :e :b :c :d] distances)))
    (is (= nil (calculate-distance [:a :e :d] distances)))
    (is (= 2 (count (find-trips :c :c 3 distances))))
    (is (= 3 (count (find-trips-exactly :a :c 4 distances))))
    (is (= 9 (shortest-route :a :c distances)))
    (is (= 9 (shortest-route :b :b distances)))
    (is (= #{[:c :d :c][:c :e :b :c][:c :e :b :c :d :c][:c :d :c :e :b :c][:c :d :e :b :c][:c :e :b :c :e :b :c][:c :e :b :c :e :b :c :e :b :c]}(find-routes :c :c 30 distances)))))
