function apply(input) {
    var metric = JSON.parse(input);
    var value = metric.extraProperties.entity.executiontime.count;
    var output = {
        suffix: "time",
        units: "ms",
        component: "methodecho",
        application: "problematic",
        value: value
    };
    return JSON.stringify(output);
}




