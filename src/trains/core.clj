(ns trains.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

; city-seq in the format of keywords of cities
; distances is a map of distances by dest and origin; ex: {:a {:b 2 :c 3}}
(defn calculate-distance [city-seq distances]
  (get-in distances city-seq))
