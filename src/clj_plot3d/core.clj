;; Translation to clojure of the jzy3d demos at: 
;;     https://github.com/jzy3d/jzy3d-tutorials/tree/master/src/main/java/org/jzy3d/demos
;; Copied from: 
;;     https://gist.github.com/scottdw/5861918

(ns clj-plot3d.core
  (:import [java.util Random]
           [org.jzy3d.analysis AnalysisLauncher AbstractAnalysis]
           [org.jzy3d.chart Chart]
           [org.jzy3d.chart.factories AWTChartComponentFactory]
           [org.jzy3d.colors Color ColorMapper]
           [org.jzy3d.colors.colormaps ColorMapRainbow]
           [org.jzy3d.maths Coord3d Range]
           [org.jzy3d.plot3d.builder Builder Mapper]
           [org.jzy3d.plot3d.builder.concrete OrthonormalGrid]
           [org.jzy3d.plot3d.primitives Scatter Shape]
           [org.jzy3d.plot3d.rendering.canvas Quality]))
 
(defn surface-demo []
  (let [chart (atom nil)]
    (AnalysisLauncher/open (proxy [AbstractAnalysis] []
                             (^Chart getChart []
                               @chart)
 
                             (^String getName []
                               "Surface Demo")
 
                             (^String getCanvasType []
                               "newt")
 
                             (init []
                               (let [mapper (proxy [Mapper] []
                                              (^double f [^double x ^double y]
                                                (* x (Math/sin (* x y)))))
                                     range (Range. -3, 3)
                                     steps 80
                                     ^Shape surface (Builder/buildOrthonormal
                                              (OrthonormalGrid. range steps range steps)
                                              mapper)
                                     bounds (.getBounds surface)]
                                 (reset! chart (doto (AWTChartComponentFactory/chart ^Quality Quality/Advanced ^String (.getCanvasType this))
                                                 (->
                                                  .getScene
                                                  .getGraph (.add (doto surface
                                                                    (.setColorMapper (ColorMapper. (ColorMapRainbow.)
                                                                                                   (.getZmin bounds)
                                                                                                   (.getZmax bounds)
                                                                                                   (Color. 1.0 1.0 1.0 0.5)))
                                                                    (.setFaceDisplayed true)
                                                                    (.setWireframeDisplayed false))))))))))))
 
(defn scatter-demo []
  (let [chart (atom nil)]
    (AnalysisLauncher/open (proxy [AbstractAnalysis] []
                             (^Chart getChart []
                               @chart)
 
                             (^String getName []
                               "Scatter Demo")
 
                             (^String getCanvasType []
                               "newt")
 
                             (init []
                               (let [size 50000
                                     points (make-array Coord3d size)
                                     colors (make-array Color size)
                                     r (doto (Random.) (.setSeed 0))]
                                 (dotimes [i size]
                                   (let [t (float 0.5)
                                         x (float (- (.nextFloat r) t))
                                         y (float (- (.nextFloat r) t))
                                         z (float (- (.nextFloat r) t))]
                                   (aset points i (Coord3d. x y z))
                                   (aset colors i (Color. x y z (float 0.25)))))
                                 (reset! chart (doto (AWTChartComponentFactory/chart ^Quality Quality/Advanced ^String (.getCanvasType this))
                                                 (->
                                                  .getScene
                                                  (.add (Scatter. points colors)))))))))))
