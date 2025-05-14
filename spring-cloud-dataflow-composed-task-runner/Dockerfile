ARG IMAGE_REPO
FROM --platform=linux/amd64 $IMAGE_REPO/ubi8/openjdk-21-runtime

ARG TARGETARCH

LABEL com.vmware.cp.artifact.flavor="sha256:c50c90cfd9d12b445b011e6ad529f1ad3daea45c26d20b00732fae3cd71f6a83" \
      org.opencontainers.image.base.name="ubi8/openjdk-21-runtime" \
      org.opencontainers.image.created="2024-12-10T00:28:13Z" \
      org.opencontainers.image.description="Application packaged by Broadcom, Inc." \
      org.opencontainers.image.documentation="https://github.com/bitnami/containers/tree/main/bitnami/spring-cloud-dataflow/README.md" \
      org.opencontainers.image.licenses="Apache-2.0" \
      org.opencontainers.image.ref.name="2.11.5-ubi8-r1" \
      org.opencontainers.image.source="https://github.com/bitnami/containers/tree/main/bitnami/spring-cloud-dataflow" \
      org.opencontainers.image.title="spring-cloud-dataflow" \
      org.opencontainers.image.vendor="Broadcom, Inc." \
      org.opencontainers.image.version="2.11.5"

ENV HOME="/" \
    OS_ARCH="ubi8-amd64" \
    OS_FLAVOUR="ubi8" \
    OS_NAME="linux"

USER root
ARG ARTIFACT_REPO_YUM
RUN curl http://${ARTIFACT_REPO_YUM}/localrepo/repo/rhel8.repo -o /etc/yum.repos.d/ubi.repo
RUN microdnf update -y && microdnf install -y dos2unix procps-ng zlib \
    && microdnf clean all

COPY prebuildfs / 
COPY rootfs /

RUN chmod +x /yq_linux_amd64 && mv /yq_linux_amd64 /usr/local/bin/yq

SHELL ["/bin/bash", "-o", "errexit", "-o", "nounset", "-o", "pipefail", "-c"]

RUN find / -perm /6000 -type f -exec chmod a-s {} \; || true


COPY target/spring-cloud-dataflow-composed-task-runner-2.11.5.jar /opt/bitnami/spring-cloud-dataflow-composed-task-runner/scdf-composed-task-runner.jar


RUN find /opt/bitnami/scripts -type f -exec dos2unix {} \;
RUN find /opt/bitnami/scripts -type f -exec chmod +x {} \;

RUN chmod -R u+rwX,g+rwX /opt /usr/sbin
RUN mkdir /.m2 && chmod -R g+rwX /.m2
RUN /opt/bitnami/scripts/java/postunpack.sh

ENV APP_VERSION="2.11.5" \
    BITNAMI_APP_NAME="spring-cloud-dataflow-composed-task-runner" \
    PATH="/opt/bitnami/common/bin:$PATH"

USER 1001
ENTRYPOINT [ "/opt/bitnami/scripts/scdf-composed-task-runner/entrypoint.sh" ]
CMD [ "--help" ]