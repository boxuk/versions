
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

# Caveats

Currently versions only handles numerical versions, it does not take into
account formats like release candidates, or beta/alpha strings, etc...

## License

Dual licensed under GPLv2 and MIT.

