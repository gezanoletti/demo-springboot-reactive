package com.gezanoletti.demospringbootreactive.common;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class ListIdRequest
{
    @NotEmpty
    List<Integer> ids;
}
