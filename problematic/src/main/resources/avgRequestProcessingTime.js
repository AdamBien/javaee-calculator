function apply(input) {
    var metric = JSON.parse(input);
    var value = metric.extraProperties.entity.processingtime.count;
    var output = {
        suffix: "avg",
        units: "ms",
        component: "requestprocessingtime",
        application: "problematic",
        value: value
    };
    return JSON.stringify(output);
}


