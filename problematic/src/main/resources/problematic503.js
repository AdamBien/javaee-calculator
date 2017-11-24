function apply(input) {
    var metric = JSON.parse(input);
    var value = metric.extraProperties.entity.count503.count;
    var output = {
        suffix: "count",
        units: "total",
        component: "unavailable503",
        application: "problematic",
        value: value
    };
    return JSON.stringify(output);
}


