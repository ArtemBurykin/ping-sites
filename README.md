# ping-urls

The script allows you to ping pages of a website to check their status, then sends the result to a slack channel.
Also if the --show-only-fails flag is specified it outputs only fails (that is when a site is not available).

## Usage

Just run the script with a config file.
```
    # --show-only-fails allows to output only fails (when a site is not available)
    $ lein run {config-file} [--show-only-fails]
```
An example of the config file:
```json
{
    "slackHook": "http://slack-hook.ru/..",
    "urls": [
        "http://url1.com",
        "http://url2.com"
    ]
}
```

## License

Copyright © 2019

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
