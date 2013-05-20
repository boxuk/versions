
# Versions

Versions is a super-simple library for comparing version numbers.

## Usage

Just pass version strings to the comparison functions.

```clojure
(use boxuk.versions)

(later-version? "1.0.0" "1.0.1") ; true
(later-version? "2.1.0" "1.2.0") ; false

(earlier-version? "1.0" "0.0.9" ) ; true
(earlier-version? "0.9.0" "0.10" ) ; false

(same-version? "0.1" "0.1.0.0.0.0" ) ; true
(same-version? "2.1" "1.2") ; false
```

You can also filter a sequence for the latest or earliest version.

```clojure
(latest-version ["1.1" "2.3"]) ; "2.3"
(earliest-version ["2.4" "3.4.2"]) ; "2.4"
```

## Stable/Unstable

Versions can make a distinction between stable and unstable versions.
Unstable versions are anything postfixed with for example _alpha_, _RC_, or _SNAPSHOT_.

```clojure
(stable? "1.2.3-SNAPSHOT") ; false
(unstable? "1.2.3-alpha2") ; true

(latest-stable ["1.2.2" "1.2.2-SNAPSHOT"] ; "1.2.2"
```

The order of comparison for unstable versions is:

```clojure
(> "SNAPSHOT" "RC" "beta" "alpha")
```

# Installation

Versions is available from [Clojars](http://clojars.org/versions)

## ClojureScript

Versions also supports being used via ClojureScript.

## License

Dual licensed under GPLv2 and MIT.

