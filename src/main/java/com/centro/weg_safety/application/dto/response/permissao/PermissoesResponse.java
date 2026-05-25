package com.centro.weg_safety.application.dto.response.permissao;

import java.util.List;

public record PermissoesResponse(
        List<PermissaoResponse> whitelist,
        List<PermissaoResponse>blacklist
) {
}
