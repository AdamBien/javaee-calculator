function apply(input) {
    var metric = JSON.parse(input);
    var value = metric.extraProperties.entity.currentthreadsbusy.count;
    var output = {
        suffix: "total",
        units: "count",
        component: "http1currentthreadsbusy",
        application: "problematic",
        value: value
    };
    return JSON.stringify(output);
}




