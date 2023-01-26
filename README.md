# s3-read-large-files

### Upload file
```shell
brew install s3cmd
s3cmd --configure
s3cmd ls

s3cmd mb s3://test-offers
s3cmd put --acl-private offers.csv s3://test-offers/offers.csv
```