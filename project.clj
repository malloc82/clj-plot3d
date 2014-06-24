(defproject clj-plot3d "0.1.0-SNAPSHOT"
  :description "3D plotting library using Jzy3d"
  :url "https://github.com/malloc82/clj-plot3d"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [["jzy3d-snapshots" "http://www.jzy3d.org/maven/snapshots"]
                 ["jzy3d-releases"  "http://www.jzy3d.org/maven/releases"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.jzy3d/jzy3d-api "0.9.1"]])
