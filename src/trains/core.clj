(ns trains.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

; city-seq in the format of keywords of cities
; distances is a map of distances by dest and origin; ex: {:a {:b 2 :c 3}}
; returns the distance of the whole path or nil if such path is not possible
(defn calculate-distance [city-seq distances]
  (let [routes (partition 2 1 city-seq)]
    (try
      (reduce #(+ %1 (get-in distances %2))
              0
              routes)
      (catch NullPointerException e nil))))
