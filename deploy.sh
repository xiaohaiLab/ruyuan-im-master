#!/bin/bash -e
project="$1"
profile="$2"
# 镜像库地址
registry=''
# 读取版本号
path="${project}/target/maven-archiver/pom.properties"
function prop {
	[ -f "$path" ] && grep -P "^\s*[^#]?${1}=.*$" "$path" | cut -d'=' -f2
}
version="$(prop 'version')"
# 发布镜像
result=$(rancher kubectl set image deploy "$project"-"$profile" "$project"-"$profile"="$registry""$project"-"$profile":"${version}" -n "$profile")
if [[ "$(echo "$result" | grep updated)" != ""  ]]; then
  ## 镜像版本不相同
  echo 'UPDATED'
else
  ## 镜像版本相同
  echo 'NOT UPDATE'
  ## 重启服务
	rancher kubectl rollout restart deployment "$project"-"$profile" -n "$profile"
fi